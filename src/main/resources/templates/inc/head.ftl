<#-- @ftlvariable name="user" type="com.springbootbbs.entiry.User" -->
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${title!}</title>
	<link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
	<script src="/static/_js/jquery.min.js"></script>
	<script src="/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="/static/font-awesome-4.6.3/css/font-awesome.min.css"></script>

	<link rel="stylesheet" href="/static/_css/main.css">
</head>
<body>

<div id="body_container">

	<div class="navbar navbar-inverse">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" href="/">Spring Boot BBS</a>
				<div class="nav-collapse">
					<ul class="nav">
						<#assign uri = springMacroRequestContext.requestUri>
						<li <#if uri == "/" || uri?contains("/topic")>class="active"</#if>><a href="/">社区</a></li>
					</ul>
					<form class="navbar-search pull-left" action="">
						<input type="text" class="search-query span2" placeholder="搜索帖子">
					</form>
					<ul class="nav pull-right">
						<#if user??>
							<li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">${user.username} <b class="caret"></b></a>
								<ul class="dropdown-menu">
									<li><a href="#">Action</a></li>
									<li><a href="#">Another action</a></li>
									<li><a href="#">Something else here</a></li>
									<#if user.username == "admin"><li><a href="/admin">管理后台</a></li></#if>
									<li class="divider"></li>
									<li><a href="/logout">退出</a></li>
								</ul>
							</li>
						<#else>
							<li <#if springMacroRequestContext.requestUri?startsWith("/login")>class="active"</#if>><a href="/login">登陆</a></li>
							<li <#if springMacroRequestContext.requestUri?startsWith("/user/register")>class="active"</#if>><a href="/user/register">注册</a></li>
						</#if>
					</ul>
				</div><!-- /.nav-collapse -->
			</div>
		</div><!-- /navbar-inner -->
	</div>
