<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${title!}</title>
	<link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
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
						<li class="active"><a href="#">社区</a></li>
					</ul>
					<form class="navbar-search pull-left" action="">
						<input type="text" class="search-query span2" placeholder="搜索帖子">
					</form>
					<ul class="nav pull-right">
						<li><a href="#">Link</a></li>
						<li class="divider-vertical"></li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">${user.username} <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="#">Action</a></li>
								<li><a href="#">Another action</a></li>
								<li><a href="#">Something else here</a></li>
								<li class="divider"></li>
								<li><a href="#">Separated link</a></li>
							</ul>
						</li>
					</ul>
				</div><!-- /.nav-collapse -->
			</div>
		</div><!-- /navbar-inner -->
	</div>
