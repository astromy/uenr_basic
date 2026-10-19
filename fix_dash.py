import re  
with open('src/main/resources/templates/dash_oard.html', 'r') as f:  
    content = f.read()  
content = content.replace(' th:onload="getdashboard();"', '')  
