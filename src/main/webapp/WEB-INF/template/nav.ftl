<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#blog-navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/#">${site.blogName}</a>
        </div>
        <p class="navbar-text navbar-left">${(site.blogDescription)!}</p>

        <div class="collapse navbar-collapse navbar-right" id="blog-navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">分类 <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        [#list categoryCount?keys as categoryKey]
                        <li>
                            <a href="/${"${site.blogCategoryKey}/${categoryKey?url}"}">${categoryKey} <span
                                    class="badge pull-right">${categoryCount[categoryKey]}</span></a>
                        </li>
                        [/#list]
                        <li class="divider"></li>
                        <li><a href="/category">全部</a></li>
                    </ul>
                </li>
                <li><a href="/archives">存档</a></li>
                <li><a href="/tags">标签</a></li>
                <li><a href="/about">关于我</a></li>
            </ul>
            <form class="navbar-form navbar-right" role="search" method="post" action="/search">
                <div class="form-group has-feedback">
                    <input type="search" class="form-control" placeholder="Search">
                    <i class="form-control-feedback glyphicon glyphicon-search"></i>
                </div>
            </form>
        </div>
    </div>
</nav>