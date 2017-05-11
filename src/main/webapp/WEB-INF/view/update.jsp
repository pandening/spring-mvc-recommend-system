<%--
  Created by IntelliJ IDEA.
  User: hujian
  Date: 2017/5/6
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update</title>
</head>
<body>
<form id = "update_user" action="change.do" method="post">
    nick_name...:<input name="user_nick_name" type="text"/><br/>
    new_nickname:<input name="new_nick_name" type="text"/><br/>
    new_phone...:<input name="new_phone" type="text"/> <br/>
    new E-Mail..:<input name="new_email" type="text"><br/>
    Submit......:<input name="Update" type="submit" value="ok">
</form>
</body>
</html>
