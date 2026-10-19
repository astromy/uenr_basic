content = open("AcademicResponse.java","r").read()
with open("src/main/java/com/UBSDB/UnerBasicSchoolFinancialSystem/Academic/AcademicResponse.java", "w") as f:
    f.write(content)
print("done")