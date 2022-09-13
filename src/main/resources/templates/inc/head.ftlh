<#-- @ftlvariable name="searchWord" type="String" -->
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${title!}</title>
    <meta content="${_csrf.token}" name="csrftoken">
    <meta content="${_csrf.headerName}" name="csrftoken_header_name">
    <meta content="${_csrf.parameterName}" name="csrftoken_parameter_name">
	<link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/font-awesome-4.6.3/css/font-awesome.min.css">
	<script src="/static/_js/jquery.min.js"></script>
	<script src="/static/layui/layer/layer.js"></script>
	<script src="/static/_js/main.js"></script>
	<script src="/static/_js/head.js"></script>
	<script src="/static/bootstrap/js/bootstrap.min.js"></script>

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
						<li <#if uri == "/" || uri?contains("/topic")>class="active"</#if>><a href="/">${local("club")}</a></li>
					</ul>
					<form class="navbar-search pull-left" action="/">
						<input type="text" name="searchWord" value="<#if searchWord??>${searchWord}</#if>" class="search-query span2" placeholder="${local("top_search")}" autocomplete="off">
					</form>
					<ul class="nav pull-right">
						<#if user??>
							<li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">${user.username} <b class="caret"></b></a>
								<ul class="dropdown-menu">
									<li><a href="/profile/basic">${local("profile")}</a></li>
									<li><a href="/profile/password">${local("profile_password")}</a></li>
									<li><a href="#">Something else here</a></li>
									<#if user.username == "admin"><li><a href="/admin">${local("admin_panel")}</a></li></#if>
									<li class="divider"></li>
									<li><a href="/logout">${local("logout")}</a></li>
								</ul>
							</li>
						<#else>
							<li <#if springMacroRequestContext.requestUri?startsWith("/login")>class="active"</#if>><a href="/login">${local("login")}</a></li>
							<li <#if springMacroRequestContext.requestUri?startsWith("/user/register")>class="active"</#if>><a href="/user/register">${local("register")}</a></li>
						</#if>
					</ul>
				</div><!-- /.nav-collapse -->
			</div>
		</div><!-- /navbar-inner -->
	</div>
