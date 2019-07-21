from flask import Flask,request,jsonify,json
from math import radians, cos, sin, asin, sqrt
from flask_mysqldb import MySQL
app=Flask(__name__)
app.config['MYSQL_HOST']='127.0.0.1'
app.config['MYSQL_USER']='root'
mysql=MySQL(app)
@app.route('/blog', methods=['POST'])
def index():

    cur=mysql.connection.cursor()
    id=request.form['id']
    blogdata=request.form.get('blogdata')
    blogname=request.form.get('blogname')
    blogdate=request.form.get('blogdate')
    imageurl=request.form.get('imageurl')
    cur.execute('''USE datatry''' )
    cur.execute("""INSERT INTO blooddonation (id,blogdata,blogname,blogdate,imageurl) VALUES (%s,%s,%s,%s,%s) """,(id,blogdata,blogname,blogdate,imageurl))
    mysql.connection.commit() 
    return jsonify({'status':'true','message':'success'})
@app.route('/blog',methods=['GET'])
def getblog():
    cur=mysql.connection.cursor()

    cur.execute('''USE datatry''')
    cur.execute("""SELECT * FROM blooddonation""")
    row_headers=[x[0] for x in cur.description]
   
    c=cur.fetchall()
    mysql.connection.commit()
    json_data=[]
    for result in c:
        json_data.append(dict(zip(row_headers,result)))
    result = {}
    result['data'] = json_data
    return (jsonify(result))
@app.route('/delete',methods=['DELETE'])
def dele():
    cur=mysql.connection.cursor()
    id=request.form.get('id')
    #txt="blabla"
    cur.execute('''USE datatry''')
    
    cur.execute("""DELETE FROM blooddonation WHERE id=%s""",(id,))
   
    mysql.connection.commit()
    return jsonify({'status':'true','message':'success'})
@app.route('/tip', methods=['POST'])
def indexing():

    cur=mysql.connection.cursor()
    tip1=request.form['tip']
    
    cur.execute('''USE datatry''' )
    cur.execute("""INSERT INTO tip (tip) VALUES (%s) """,(tip1,))
    mysql.connection.commit() 
    return jsonify({'status':'true','message':'success'})
@app.route('/tip',methods=['GET'])
def gettip():
    cur=mysql.connection.cursor()

    cur.execute('''USE datatry''')
    cur.execute("""SELECT * FROM tip""")
    row_headers=[x[0] for x in cur.description]
   
    c=cur.fetchall()
    mysql.connection.commit()
    json_data=[]
    for result in c:
        json_data.append(dict(zip(row_headers,result)))
    result = {}
    result['tips'] = json_data
    return (jsonify(result))
@app .route ('/location',methods=['POST'])
def giveloc():
    cur=mysql.connection.cursor()
    #id=request.form['id']
    name=request.form['name']
    age=request.form.get('age')
    email=request.form['email']
    username=request.form.get('username')
    password=request.form['password']
    phonenumber=request.form.get('phonenumber')
    latitude2=request.form.get('latitude2')
    longitude2=request.form.get('longitude2')
    bloodgroup=request.form.get('bloodgroup')
    cur.execute('''USE datatry''' )
    cur.execute("""INSERT INTO location_register (name, age, email, username, password, phonenumber, latitude2, longitude2, bloodgroup) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s) """,(name, age, email, username, password, phonenumber, latitude2, longitude2, bloodgroup))
    # cur.execute("""INSERT INTO locationsregister (name,age,username,password,phonenumber,latitude2,longitude2,bloodgroup) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s) """,(name,age,email,username,password,phonenumber,latitude2,longitude2,bloodgroup))
    mysql.connection.commit() 
    return jsonify({'status':'true','message':'success'})
@app .route ('/surveytest',methods=['POST'])
def survey():
    cur=mysql.connection.cursor()
    #id=request.form['id']
    register=request.form['register']
    survey=request.form.get('survey')
    survey_result=request.form['survey_test']
    phonenumber=request.form.get('phonenumber')
    cur.execute('''USE datatry''' )
    cur.execute("""INSERT INTO survey_test (register,survey,survey_test, phonenumber) VALUES (%s,%s,%s,%s) """,(register, survey,survey_result,phonenumber))
    # cur.execute("""INSERT INTO locationsregister (name,age,username,password,phonenumber,latitude2,longitude2,bloodgroup) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s) """,(name,age,email,username,password,phonenumber,latitude2,longitude2,bloodgroup))
    mysql.connection.commit() 
    return jsonify({'status':'true','message':'success'})
@app .route ('/surveytests',methods=['POST'])
def surveyrule():
    cur=mysql.connection.cursor()
    data = {}
    loc=[]
    #id=request.form['id']
    phonenumber=request.form.get('phonenumber')
    cur.execute('''USE datatry''' )

    cur.execute("""SELECT register,survey,survey_test FROM survey_test where phonenumber =%s """,(phonenumber,))
    row_headers=[x[0] for x in cur.description]
    c=cur.fetchall()
    for result in c:
            
        loc.append(dict(zip(row_headers,result)))
    data['survey']=loc
    return(jsonify(data))
@app .route ('/accept_request',methods=['POST'])
def send_accept():
    cur=mysql.connection.cursor()
    data = {}
    loc=[]
    #id=request.form['id']
    name=request.form.get('name')
    email=request.form.get('email')
    phonenumber=request.form.get('phonenumber')
    name_user=request.form.get('name_user')
    email_user=request.form.get('email_user')
    phonenumber_user=request.form.get('phonenumber_user')
    cur.execute('''USE datatry''' )
    cur.execute("""INSERT INTO send_accept (name,email,phonenumber, name_user,email_user,phonenumber_user) VALUES (%s,%s,%s,%s,%s,%s) """,(name, email,phonenumber,name_user,email_user,phonenumber_user))
    mysql.connection.commit() 
    return jsonify({'status':'true','message':'success'})
@app .route ('/accept_send_request',methods=['POST'])
def accept_send_request():
    cur=mysql.connection.cursor()
    data = {}
    loc=[]
    #id=request.form['id']
    phonenumber=request.form.get('phonenumber')
    cur.execute('''USE datatry''' )

    cur.execute("""SELECT phonenumber_user from send_accept where phonenumber =%s """,(phonenumber,))
    row_headers=[x[0] for x in cur.description]
    c=cur.fetchall()
    for result in c:
            
        loc.append(dict(zip(row_headers,result)))
    data['accept']=loc
    # cur.execute("""INSERT INTO locationsregister (name,age,username,password,phonenumber,latitude2,longitude2,bloodgroup) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s) """,(name,age,email,username,password,phonenumber,latitude2,longitude2,bloodgroup))
    mysql.connection.commit() 
    return jsonify(data)
@app .route ('/surveytestcount',methods=['POST'])
def surveycount():
    cur=mysql.connection.cursor()
    data = {}
    loc=[]
    #id=request.form['id']
    phonenumber=request.form.get('phonenumber')
    cur.execute('''USE datatry''' )
    cur.execute("""SELECT count(*) FROM survey_test where phonenumber =%s """,(phonenumber,))
    row_headers=[x[0] for x in cur.description]
    c=cur.fetchall()
    for result in c:
            
        loc.append(dict(zip(row_headers,result)))
    data['count']=loc
    # cur.execute("""INSERT INTO locationsregister (name,age,username,password,phonenumber,latitude2,longitude2,bloodgroup) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s) """,(name,age,email,username,password,phonenumber,latitude2,longitude2,bloodgroup))
    mysql.connection.commit() 
    return jsonify(data)
@app .route ('/surveytestsupdate',methods=['PUT'])
def surveyupdate():
    cur=mysql.connection.cursor()
    data = {}
    loc=[]
    #id=request.form['id']
     #id=request.form['id']
    register=request.form['register']
    survey=request.form.get('survey')
    survey_result=request.form['survey_test']
    phonenumber=request.form.get('phonenumber')
    cur.execute('''USE datatry''' )

    cur.execute("""UPDATE survey_test SET register=%s,survey=%s,survey_test=%s  where phonenumber =%s """,(register,survey,survey_result,phonenumber))
    mysql.connection.commit()
    return jsonify({'status':'true','message':'success'})
   
    # cur.execute("""INSERT INTO locationsregister (name,age,username,password,phonenumber,latitude2,longitude2,bloodgroup) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s) """,(name,age,email,username,password,phonenumber,latitude2,longitude2,bloodgroup))
    mysql.connection.commit() 
    return jsonify({'status':'true','message':'success'})
@app .route ('/surveytestsdelete',methods=['DELETE'])
def surveydelete():
    cur=mysql.connection.cursor()
    phonenumber=request.form.get('phonenumber')
    #txt="blabla"
    cur.execute('''USE datatry''')
    
    cur.execute("""DELETE FROM survey_test WHERE phonenumber=%s""",(phonenumber,))
   
    mysql.connection.commit()
    return jsonify({'status':'true','message':'success'})

@app.route('/locations',methods=['POST'])
def haversine():

    cur=mysql.connection.cursor()
    bloodgroup=request.form.get('bloodgroup')
    lat_1=request.form['latitude1']
    lon_1=request.form.get('longitude1')
    lat2=[]
    lon2=[]
    lat1=float(lat_1)
    lon1=float(lon_1)
    cur.execute('''USE datatry''')

    cur.execute("""SELECT count(*) FROM location_register where bloodgroup=%s """,(bloodgroup,))
    length=cur.fetchone()[0]
    o=cur.fetchall()
    data = {}
    loc=[]
    lat=[]
    lon=[]
    #for x in range(length):
    p=0
    cur.execute("""SELECT latitude2 FROM location_register where bloodgroup=%s """,(bloodgroup,))
    lat2=cur.fetchall()[length-1][0]
    cur.execute("""SELECT longitude2 FROM location_register where bloodgroup=%s """,(bloodgroup,))
    lon2=cur.fetchall()[length-1][0]
    p=p+1
    latitude=lat2
    #print(latitude)
    longitude=lon2
    b=2
    if b<10:
           
        p=0    
        cur.execute("""SELECT latitude2,longitude2,name,email,phonenumber FROM location_register where bloodgroup=%s """,(bloodgroup,))
        row_headers=[x[0] for x in cur.description]
        c=cur.fetchall()
        
        print(len(c))
        length=len(c)
        for result in c:
            for v in result[0:1]:
                for p in range(length):
                    cur.execute("""SELECT latitude2 FROM location_register where bloodgroup=%s """,(bloodgroup,))
                    lat2=cur.fetchall()[p][0]
                    cur.execute("""SELECT longitude2 FROM location_register where bloodgroup=%s """,(bloodgroup,))
                    lon2=cur.fetchall()[p][0]
                    #latitude,longitude,dlon,dlat,a,c,r,b=0
                    latitude=lat2
                    print(latitude)
                    longitude=lon2
                    lat1=float(lat_1)
                    lon1=float(lon_1)
                    lon1, lat1, lon2, lat2 = map(radians, [lon1, lat1, lon2, lat2])
                    dlon = lon2 - lon1 
                    dlat = lat2 - lat1 
                    a = sin(dlat/2)**2 + cos(lat1) * cos(lat2) * sin(dlon/2)**2
                    c = 2 * asin(sqrt(a)) 
                    r = 6371 # Radius of earth in kilometers. Use 3956 for miles        
                    b = c * r
                    print(b)
                    radius=10.00
                    if b<radius:
                        if v==latitude :
                            print(v==latitude)
                    
                            loc.append(dict(zip(row_headers,result)))
    data['locations']=loc
    return(jsonify(data))

@app .route('/getblood',methods=['POST'])
def getblood():
    cur=mysql.connection.cursor()
    data = {}
    loc=[]
    phonenumber=request.form.get('phonenumber')
    cur.execute('''USE datatry''' )
    cur.execute("""SELECT bloodgroup FROM location_register where phonenumber=%s  """,(phonenumber,))
    row_headers=[x[0] for x in cur.description]
    c=cur.fetchall()
    for result in c:
            
        loc.append(dict(zip(row_headers,result)))
    data['blood']=loc
    mysql.connection.commit() 
    return(jsonify(data))
@app .route('/getage',methods=['POST'])
def getAge():
    cur=mysql.connection.cursor()
    data = {}
    loc=[]
    phonenumber=request.form.get('phonenumber')
    cur.execute('''USE datatry''' )
    cur.execute("""SELECT age FROM location_register where phonenumber=%s  """,(phonenumber,))
    row_headers=[x[0] for x in cur.description]
    c=cur.fetchall()
    for result in c:
            
        loc.append(dict(zip(row_headers,result)))
    data['age']=loc
    mysql.connection.commit() 
    return(jsonify(data))
@app .route ('/request',methods=['POST'])
def setreq():
    cur=mysql.connection.cursor()
    #id=request.form['id']
    name=request.form['name']
    email=request.form['email']
    age=request.form['age']
    username=request.form.get('username')
    password=request.form.get('password')
    phonenumber=request.form.get('phonenumber')
    latitude2=request.form.get('latitude1')
    longitude2=request.form.get('longitude1')
    bloodgroup=request.form.get('bloodgroup')
    cur.execute('''USE datatry''' )
    cur.execute("""INSERT INTO request_data (name, age, email, username, password, phonenumber, latitude1, longitude1, bloodgroup) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s) """,(name, age, email, username, password, phonenumber, latitude2, longitude2, bloodgroup))
    # cur.execute("""INSERT INTO locationsregister (name,age,username,password,phonenumber,latitude2,longitude2,bloodgroup) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s) """,(name,age,email,username,password,phonenumber,latitude2,longitude2,bloodgroup))
    mysql.connection.commit() 
    return jsonify({'status':'true','message':'success'})   
@app .route('/requests',methods=['POST'])
def reqget():
    cur=mysql.connection.cursor()
    blood=request.form.get('bloodgroup')
    bloodgroup=[]
    bloodgroupuser=[]
    #lat_1=request.form['latitude2']
    #lon_1=request.form.get('longitude2')
    lat2=[]
    lon2=[]
    lat1=[]
    lon1=[]
    cur.execute('''USE datatry''')

    cur.execute("""SELECT count(*) FROM request_data where bloodgroup=%s """,(blood,))
    length=cur.fetchone()[0]
    cur.execute("""SELECT count(*) FROM location_register where bloodgroup=%s""",(blood,))
    leng=cur.fetchone()[0]
    

    data = {}
    loc=[]
    for y in range(length):
        cur.execute("""SELECT bloodgroup FROM request_data where bloodgroup=%s """,(blood,))
        bloodgroup=cur.fetchall()[y][0]
        
        for x in range(1):
            cur.execute("""SELECT bloodgroup FROM location_register  where bloodgroup=%s """,(blood,))
            bloodgroupuser=cur.fetchall()[x][0]
            
            cur.execute("""SELECT latitude1 FROM request_data where bloodgroup=%s """,(bloodgroup,))
            lat1=cur.fetchall()[y][0]
            cur.execute("""SELECT longitude1 FROM request_data where bloodgroup=%s """,(bloodgroup,))
            lon1=cur.fetchall()[y][0]
            cur.execute("""SELECT latitude2 FROM location_register where bloodgroup=%s """,(bloodgroupuser,))
            lat2=cur.fetchall()[x][0]
            cur.execute("""SELECT longitude2 FROM location_register where bloodgroup=%s """,(bloodgroupuser,))
            lon2=cur.fetchall()[x][0]
            lon1, lat1, lon2, lat2 = map(radians, [lon1, lat1, lon2, lat2])
            dlon = lon2 - lon1 
            dlat = lat2 - lat1 
            a = sin(dlat/2)**2 + cos(lat1) * cos(lat2) * sin(dlon/2)**2
            c = 2 * asin(sqrt(a)) 
            r = 6371 # Radius of earth in kilometers. Use 3956 for miles        
            b = c * r
            radius=10.00
            cur.execute("""SELECT name,email,phonenumber FROM request_data where bloodgroup=%s  """,(bloodgroup,))
            row_headers=[x[0] for x in cur.description]
            c=cur.fetchall()
            for result in c:
                if b<radius:
                    loc.append(dict(zip(row_headers,result)))
            data['request']=loc  
        mysql.connection.commit()    
        return(jsonify(data))

            

    
   
        
       
           
            #data['lng']=longitude


    #     if b<radius:
           
    #         row_headers=[x[0] for x in cur.description]
    #         c=cur.fetchall()
    #         mysql.connection.commit()
    #         json_data=[]
            
    #         for result in c:
               
    #             json_data.append(dict(zip(row_headers,result)))
    #     result = {}
    # result['location'] = json_data
    return (jsonify(data))


if __name__=='__main__':
    app.debug = True
    app.run(host='0.0.0.0') 
