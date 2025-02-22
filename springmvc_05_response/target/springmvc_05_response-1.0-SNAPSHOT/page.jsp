<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Info</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- 引入jQuery -->
</head>
<body>

    <h2>Hello Spring MVC!</h2>

    <div>
        <!-- 显示由控制器传递过来的实体 user 的值-->
        <div th:text="${user.name}"></div> <!-- 显示用户的姓名 -->
        <div th:text="${user.age}"></div> <!-- 显示用户的年龄 -->

        <!-- 显示英文名 -->
        <button id="englishNameBtn">myEnglishName</button>
        <div id="englishName"></div> <!-- 这个 div 用于显示英文名 -->
    </div>

    <script>
        // 点击按钮时触发AJAX请求
        $('#englishNameBtn').click(function() {
            $.ajax({
                url: '/engNm', // 请求的 URL
                method: 'GET',
                success: function(data) {
                    // 显示返回的英文名
                    $('#englishName').text('英文名: ' + data);
                },
                error: function(error) {
                    console.log('Error fetching data:', error);
                }
            });
        });
    </script>

</body>
</html>
