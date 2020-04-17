<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>简单页面</title>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
</head>
<body>
<div id="mood">
    <b>说说列表</b>
    <c:forEach items="${moods}" var="mood">
        <b>用户：</b><span id="userName">${mood.userName}</span><br>
        <b>说说内容：</b><span id="content">${mood.content}</span><br>
        <b>发表时间：</b><span id="pulish_time">${mood.publishTime}</span><br>
        <b>点赞数：</b><span id="praise_num">${mood.praiseNum}</span><br>
        <div style="margin-left: 350px">
            <a id="praise" href="/mood/${mood.id}/praise?userId=${mood.userId}">赞</a>

            <a id="praiseForRedis" href="/mood/${mood.id}/praiseForRedis?userId=${mood.userId}">赞</a>
        </div>
    </c:forEach>
</div>
</body>
</html>
