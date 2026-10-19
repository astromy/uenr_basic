import os  
filepath = os.path.join(os.getcwd(), 'src', 'main', 'java', 'com', 'UBSDB', 'UnerBasicSchoolFinancialSystem', 'Academic', 'AcademicResponse.java')  
content = open('academic_content.txt', 'r').read()  
with open(filepath, 'w', encoding='utf-8') as f:  
    f.write(content)  
print('Written: ' + str(os.path.getsize(filepath)) + ' bytes')  
