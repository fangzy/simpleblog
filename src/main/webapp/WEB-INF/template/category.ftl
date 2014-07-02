[#setting locale="en_CN"]
[#include "/WEB-INF/template/header.ftl"]
<body>
<div class="container-fluid">
    <a href="/">回到首页</a>

    <div id="main" class="row">
        <div class="col-md-8">
            <ul>
            [#list view.categoryDataList as clist]
                <li>${clist.name}<span class="badge">${clist.count}</span></li>
            [/#list]
            </ul>
        [#list view.categoryDataList as clist]
            <h1>${clist.name}</h1>

            <div class="row">
                <div class="col-md-12">
                    <ul>
                        [#list clist.blogList as blist]
                            <li>${blist.created?string("MMMMM d,yyyy")} - <a
                                    href="/${site.blogPostKey}/${blist.title?url}">${blist.title}</a></li>
                        [/#list]
                    </ul>
                </div>
            </div>
        [/#list]
        </div>
    [#include "/WEB-INF/template/side.ftl"]
    </div>
</div>
</body>
[#include "/WEB-INF/template/footer.ftl"]