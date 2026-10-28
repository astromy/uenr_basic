
(function () {
    "use strict";

    const BASE = "/FinancePortal/expenses";

    let currentPage = 0;
    let pageSize = 10;
    let searchTimer = null;
    let initialized = false;

    // --------------------------------------------------
    // CSRF
    // --------------------------------------------------

    function getCsrfHeaders() {
        const headers = {
            "Content-Type": "application/json"
        };

        const tokenMeta = document.querySelector('meta[name="_csrf"]');
        const headerMeta = document.querySelector(
            'meta[name="_csrf_header"]'
        );

        if (tokenMeta && headerMeta) {
            headers[headerMeta.content] = tokenMeta.content;
        }

        return headers;
    }

    // --------------------------------------------------
    // Notifications
    // --------------------------------------------------

    function showMessage(message, type = "danger") {
        const container = document.getElementById("expenseFormError");

        if (container) {
            container.textContent = message;
            container.classList.remove("d-none", "alert-danger",
                "alert-success", "alert-warning");
            container.classList.add("alert", `alert-${type}`);
            return;
        }

        console[type === "danger" ? "error" : "log"](message);
    }

    function clearMessage() {
        const container = document.getElementById("expenseFormError");

        if (container) {
            container.textContent = "";
            container.classList.add("d-none");
            container.classList.remove(
                "alert-danger",
                "alert-success",
                "alert-warning"
            );
        }
    }

    // --------------------------------------------------
    // Modal handling: Bootstrap 4 and Bootstrap 5
    // --------------------------------------------------

    function showModal(modalElement) {
        if (!modalElement) {
            console.error("Expense modal element was not found.");
            return;
        }

        // Bootstrap 5: use the constructor directly.
        // Do not use getOrCreateInstance().
        if (
            window.bootstrap &&
            typeof window.bootstrap.Modal === "function"
        ) {
            let instance = null;

            if (
                typeof window.bootstrap.Modal.getInstance === "function"
            ) {
                instance =
                    window.bootstrap.Modal.getInstance(modalElement);
            }

            if (!instance) {
                instance = new window.bootstrap.Modal(modalElement);
            }

            instance.show();
            return;
        }

        // Bootstrap 4: use the jQuery modal plugin.
        if (
            window.jQuery &&
            window.jQuery.fn &&
            typeof window.jQuery.fn.modal === "function"
        ) {
            window.jQuery(modalElement).modal("show");
            return;
        }

        // Last-resort fallback when Bootstrap JS is missing.
        console.warn(
            "Bootstrap Modal API unavailable. Using basic modal fallback."
        );

        modalElement.style.display = "block";
        modalElement.classList.add("show");
        modalElement.removeAttribute("aria-hidden");
        modalElement.setAttribute("aria-modal", "true");
        modalElement.setAttribute("role", "dialog");
        document.body.classList.add("modal-open");

        let backdrop = document.getElementById("expenseModalFallbackBackdrop");

        if (!backdrop) {
            backdrop = document.createElement("div");
            backdrop.id = "expenseModalFallbackBackdrop";
            backdrop.className = "modal-backdrop fade show";
            document.body.appendChild(backdrop);

            backdrop.addEventListener("click", function () {
                hideModal(modalElement);
            });
        }
    }

    function hideModal(modalElement) {
        if (!modalElement) return;

        if (
            window.bootstrap &&
            typeof window.bootstrap.Modal === "function"
        ) {
            let instance = null;

            if (
                typeof window.bootstrap.Modal.getInstance === "function"
            ) {
                instance =
                    window.bootstrap.Modal.getInstance(modalElement);
            }

            if (instance) {
                instance.hide();
                return;
            }
        }

        if (
            window.jQuery &&
            window.jQuery.fn &&
            typeof window.jQuery.fn.modal === "function"
        ) {
            window.jQuery(modalElement).modal("hide");
            return;
        }

        modalElement.style.display = "none";
        modalElement.classList.remove("show");
        modalElement.setAttribute("aria-hidden", "true");
        modalElement.removeAttribute("aria-modal");
        document.body.classList.remove("modal-open");

        const backdrop = document.getElementById(
            "expenseModalFallbackBackdrop"
        );

        if (backdrop) backdrop.remove();
    }

    // --------------------------------------------------
    // Form handling
    // --------------------------------------------------

    function getElement(id) {
        return document.getElementById(id);
    }

    function setValue(id, value) {
        const element = getElement(id);

        if (element) {
            element.value = value == null ? "" : String(value);
        }
    }

    function getValue(id) {
        const element = getElement(id);
        return element ? element.value.trim() : "";
    }

    function resetForm() {
        const form = getElement("expenseForm");

        if (form) form.reset();

        setValue("expenseId", "");
        clearMessage();

        const saveText = getElement("expenseSaveText");
        const spinner = getElement("expenseSaveSpinner");
        const saveButton = getElement("expenseSaveBtn");

        if (saveText) saveText.textContent = "Save Expense";
        if (spinner) spinner.classList.add("d-none");
        if (saveButton) saveButton.disabled = false;
    }

    function setSaving(saving) {
        const button = getElement("expenseSaveBtn");
        const text = getElement("expenseSaveText");
        const spinner = getElement("expenseSaveSpinner");

        if (button) button.disabled = saving;
        if (text) {
            text.textContent = saving ? "Saving..." : "Save Expense";
        }
        if (spinner) {
            spinner.classList.toggle("d-none", !saving);
        }
    }

    // --------------------------------------------------
    // Open add/edit modal
    // --------------------------------------------------

    window.openExpenseModal = async function (id) {
        const modalElement = getElement("expenseModal");

        if (!modalElement) {
            console.error(
                'Cannot open expense modal: element with id="expenseModal" is missing.'
            );
            return;
        }

        resetForm();

        const title = modalElement.querySelector(".modal-title");

        if (title) {
            title.textContent = id ? "Edit Expense" : "Add Expense";
        }

        showModal(modalElement);

        if (id == null || id === "") {
            return;
        }

        try {
            const response = await fetch(
                `${BASE}/api/${encodeURIComponent(id)}`,
                {
                    method: "GET",
                    headers: { Accept: "application/json" }
                }
            );

            if (!response.ok) {
                throw new Error(
                    `Could not load expense. HTTP ${response.status}`
                );
            }

            const result = await response.json();
            const expense = result.data || result;

            setValue("expenseId", expense.id ?? expense.expenseId);
            setValue("expenseDate", expense.expenseDate);
            setValue("expenseAmount", expense.amount);
            setValue(
                "expenseCategory",
                expense.category ?? expense.expenseCategory
            );
            setValue("paymentMethod", expense.paymentMethod);
            setValue("description", expense.description);
            setValue("referenceNumber", expense.referenceNumber);
            setValue("expenseStatus", expense.status ?? expense.expenseStatus);
            setValue("termId", expense.termId);

        } catch (error) {
            console.error("Loading expense failed:", error);
            showMessage(error.message || "Failed to load expense details.");
        }
    };

    // --------------------------------------------------
    // Search filters
    // --------------------------------------------------

    function getFilters() {
        return {
            search: getValue("expenseSearch"),
            category: getValue("filterCategory"),
            status: getValue("filterStatus"),
            from: getValue("filterFrom"),
            to: getValue("filterTo")
        };
    }

    function buildListUrl() {
        const params = new URLSearchParams({
            page: String(currentPage),
            size: String(pageSize)
        });

        const filters = getFilters();

        Object.entries(filters).forEach(([key, value]) => {
            if (value) params.set(key, value);
        });

        return `${BASE}/api/list?${params.toString()}`;
    }

    // --------------------------------------------------
    // Table rendering
    // --------------------------------------------------

    function escapeHtml(value) {
        return String(value ?? "").replace(/[&<>"']/g, function (char) {
            return {
                "&": "&amp;",
                "<": "&lt;",
                ">": "&gt;",
                '"': "&quot;",
                "'": "&#39;"
            }[char];
        });
    }

    function formatAmount(value) {
        const amount = Number(value);

        if (!Number.isFinite(amount)) return escapeHtml(value);

        return amount.toLocaleString(undefined, {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        });
    }

    function renderStatus(status) {
        const value = String(status ?? "");
        const normalized = value.toLowerCase();

        let cssClass = "bg-secondary";

        if (normalized === "approved" || normalized === "paid") {
            cssClass = "bg-success";
        } else if (normalized === "pending") {
            cssClass = "bg-warning text-dark";
        } else if (
            normalized === "rejected" ||
            normalized === "cancelled"
        ) {
            cssClass = "bg-danger";
        }

        return `<span class="badge ${cssClass}">${escapeHtml(value)}</span>`;
    }

    function renderRows(expenses) {
        const tbody = getElement("expensesTbody");
        if (!tbody) return;

        if (!Array.isArray(expenses) || expenses.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="9" class="text-center py-4">
                        No expenses found.
                    </td>
                </tr>`;
            return;
        }

        tbody.innerHTML = expenses.map(function (expense) {
            const id = expense.id ?? expense.expenseId;
            const date = expense.expenseDate ?? expense.date ?? "";
            const category =
                expense.categoryName ??
                expense.categoryDisplay ??
                expense.category ??
                "";
            const description = expense.description ?? "";
            const amount = expense.amount ?? 0;
            const paymentMethod = expense.paymentMethod ?? "";
            const referenceNumber = expense.referenceNumber ?? "";
            const status = expense.status ?? expense.expenseStatus ?? "";
            const term = expense.termName ?? expense.term?.termName ?? "";

            return `
                <tr>
                    <td>${escapeHtml(date)}</td>
                    <td>${escapeHtml(category)}</td>
                    <td>${escapeHtml(description)}</td>
                    <td class="text-end">${formatAmount(amount)}</td>
                    <td>${escapeHtml(paymentMethod)}</td>
                    <td>${escapeHtml(referenceNumber)}</td>
                    <td>${renderStatus(status)}</td>
                    <td>${escapeHtml(term)}</td>
                    <td class="text-nowrap">
                        <button
                            type="button"
                            class="btn btn-sm btn-outline-primary"
                            data-expense-edit="${escapeHtml(id)}">
                            Edit
                        </button>
                        <button
                            type="button"
                            class="btn btn-sm btn-outline-danger"
                            data-expense-delete="${escapeHtml(id)}">
                            Delete
                        </button>
                    </td>
                </tr>`;
        }).join("");
    }

    function renderPagination(page, totalPages, totalElements, size) {
        const links = getElement("expensesPaginationLinks");
        const summary = getElement("expensesPageSummary");

        if (summary) {
            const first = totalElements === 0 ? 0 : page * size + 1;
            const last = Math.min((page + 1) * size, totalElements);

            summary.textContent =
                `Showing ${first}–${last} of ${totalElements} expenses`;
        }

        if (!links) return;

        if (totalPages <= 1) {
            links.innerHTML = "";
            return;
        }

        let html = `
            <li class="page-item ${page <= 0 ? "disabled" : ""}">
                <button class="page-link" type="button"
                    data-expense-page="${page - 1}">Previous</button>
            </li>`;

        for (let i = 0; i < totalPages; i++) {
            html += `
                <li class="page-item ${i === page ? "active" : ""}">
                    <button class="page-link" type="button"
                        data-expense-page="${i}">${i + 1}</button>
                </li>`;
        }

        html += `
            <li class="page-item ${page >= totalPages - 1 ? "disabled" : ""}">
                <button class="page-link" type="button"
                    data-expense-page="${page + 1}">Next</button>
            </li>`;

        links.innerHTML = html;
    }

    // --------------------------------------------------
    // Load expenses
    // --------------------------------------------------

    async function loadExpenses() {
        const tbody = getElement("expensesTbody");

        if (tbody) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="9" class="text-center py-4">
                        Loading expenses...
                    </td>
                </tr>`;
        }

        try {
            const response = await fetch(buildListUrl(), {
                method: "GET",
                headers: { Accept: "application/json" }
            });

            if (!response.ok) {
                throw new Error(
                    `Failed to load expenses. HTTP ${response.status}`
                );
            }

            const result = await response.json();
            const pageData = result.data || result;

            const expenses = Array.isArray(pageData)
                ? pageData
                : (pageData.content || pageData.items || []);

            const totalElements = Number(
                pageData.totalElements ??
                pageData.total ??
                expenses.length
            );

            const totalPages = Number(
                pageData.totalPages ??
                Math.ceil(totalElements / pageSize)
            );

            currentPage = Number(pageData.number ?? pageData.page ?? currentPage);
            pageSize = Number(pageData.size ?? pageSize);

            renderRows(expenses);
            renderPagination(
                currentPage,
                totalPages,
                totalElements,
                pageSize
            );

        } catch (error) {
            console.error("Loading expenses failed:", error);

            if (tbody) {
                tbody.innerHTML = `
                    <tr>
                        <td colspan="9" class="text-center text-danger py-4">
                            Failed to load expenses. Please try again.
                        </td>
                    </tr>`;
            }
        }
    }

    // --------------------------------------------------
    // Save expense
    // --------------------------------------------------

    async function saveExpense(event) {
        event.preventDefault();
        clearMessage();

        const id = getValue("expenseId");
        const payload = {
            id: id ? Number(id) : null,
            expenseDate: getValue("expenseDate"),
            amount: Number(getValue("amount_")),
            category: getValue("expenseCategory"),
            paymentMethod: getValue("paymentMethod"),
            description: getValue("description"),
            referenceNumber: getValue("referenceNumber"),
            status: getValue("expenseStatus"),
            termId: getValue("termId")
                ? Number(getValue("termId"))
                : null
        };
         console.log(payload);
        if (!payload.expenseDate) {
            showMessage("Please select an expense date.");
            return;
        }

        if (!Number.isFinite(payload.amount) || payload.amount <= 0) {
            alert(payload.amount);
            showMessage("Please enter a valid amount greater than zero.");
            return;
        }

        if (!payload.category) {
            showMessage("Please select an expense category.");
            return;
        }

        setSaving(true);

        try {
            const endpoint = id
                ? `${BASE}/api/update`
                : `${BASE}/api/save`;

            const response = await fetch(endpoint, {
                method: "POST",
                headers: getCsrfHeaders(),
                body: JSON.stringify(payload)
            });

            const result = await response.json().catch(() => ({}));

            if (!response.ok || result.success === false) {
                throw new Error(
                    result.message ||
                    `Failed to save expense. HTTP ${response.status}`
                );
            }

            hideModal(getElement("expenseModal"));
            await loadExpenses();

        } catch (error) {
            console.error("Saving expense failed:", error);
            showMessage(error.message || "Failed to save expense.");
        } finally {
            setSaving(false);
        }
    }

    // --------------------------------------------------
    // Delete expense
    // --------------------------------------------------

    window.deleteExpense = async function (id) {
        if (id == null || id === "") return;

        if (!window.confirm("Are you sure you want to delete this expense?")) {
            return;
        }

        try {
            const response = await fetch(
                `${BASE}/api/delete/${encodeURIComponent(id)}`,
                {
                    method: "POST",
                    headers: getCsrfHeaders()
                }
            );

            const result = await response.json().catch(() => ({}));

            if (!response.ok || result.success === false) {
                throw new Error(
                    result.message ||
                    `Failed to delete expense. HTTP ${response.status}`
                );
            }

            await loadExpenses();

        } catch (error) {
            console.error("Deleting expense failed:", error);
            window.alert(error.message || "Failed to delete expense.");
        }
    };

    // --------------------------------------------------
    // Bind events
    // --------------------------------------------------

    function bindEvents() {
        if (initialized) return;

        const form = getElement("expenseForm");
        if (!form) return;

        initialized = true;

        form.addEventListener("submit", saveExpense);

        const filterForm = getElement("expenseFilterForm");

        if (filterForm) {
            filterForm.addEventListener("submit", function (event) {
                event.preventDefault();
                currentPage = 0;
                loadExpenses();
            });
        }

        const searchInput = getElement("expenseSearch");

        if (searchInput) {
            searchInput.addEventListener("input", function () {
                clearTimeout(searchTimer);

                searchTimer = setTimeout(function () {
                    currentPage = 0;
                    loadExpenses();
                }, 300);
            });
        }

        [
            "filterCategory",
            "filterStatus",
            "filterFrom",
            "filterTo"
        ].forEach(function (id) {
            const element = getElement(id);

            if (element) {
                element.addEventListener("change", function () {
                    currentPage = 0;
                    loadExpenses();
                });
            }
        });

        const tbody = getElement("expensesTbody");

        if (tbody) {
            tbody.addEventListener("click", function (event) {
                const editButton = event.target.closest("[data-expense-edit]");
                const deleteButton = event.target.closest(
                    "[data-expense-delete]"
                );

                if (editButton) {
                    window.openExpenseModal(
                        editButton.getAttribute("data-expense-edit")
                    );
                }

                if (deleteButton) {
                    window.deleteExpense(
                        deleteButton.getAttribute("data-expense-delete")
                    );
                }
            });
        }

        const pagination = getElement("expensesPaginationLinks");

        if (pagination) {
            pagination.addEventListener("click", function (event) {
                const button = event.target.closest("[data-expense-page]");
                if (!button || button.closest(".disabled")) return;

                const page = Number(
                    button.getAttribute("data-expense-page")
                );

                if (Number.isInteger(page) && page >= 0) {
                    currentPage = page;
                    loadExpenses();
                }
            });
        }

        document.querySelectorAll(
            '[data-bs-dismiss="modal"], [data-dismiss="modal"]'
        ).forEach(function (button) {
            button.addEventListener("click", function () {
                const modalElement = button.closest(".modal");
                if (modalElement) hideModal(modalElement);
            });
        });

        loadExpenses();
    }

    // Register the public functions immediately.
    // Wait for the fragment when it is inserted dynamically.
    function initialize() {
        bindEvents();

        if (!initialized) {
            const observer = new MutationObserver(function () {
                bindEvents();

                if (initialized) {
                    observer.disconnect();
                }
            });

            observer.observe(document.body, {
                childList: true,
                subtree: true
            });
        }
    }

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", initialize);
    } else {
        initialize();
    }
})();
