[#setting locale="en_CN"]
[#include "/WEB-INF/template/header.ftl"]
<body>
<div class="container-fluid">
    <div class="page-header">
        <a href="/"><h1>${site.blogName}</h1></a>

        <h2>
            <small>${(site.blogDescription)!}</small>
        </h2>
    </div>
    <div id="main" class="row">
        <div id="post" class="col-md-8">
        [#list view.blogDataList as blogData]
            <a href="/${site.blogPostKey}/${blogData.title?url}"><h2>${blogData.title}</h2></a>
            <h4>Posted ${blogData.created?string("MMMMM d,yyyy")} by <a
                    href="${site.blogAuthorUrl}">${site.blogAuthor}</a>.Filed
                under <a href="/${site.blogCategoryKey}/${blogData.category?url}">${blogData.category}</a>.</h4>
            <article>${blogData.content}</article>
            <h6>Last modified : ${blogData.lastModified?string("MMMMM d,yyyy HH:mm:ss")}</h6>
        [/#list]
            <div>
                <ul class="pagination">
                [#if view.pageCurrent==1]
                    <li class="disabled"><a href="#">&laquo;</a></li>
                [#else]
                    <li><a href="/${site.blogPageKey}/${view.pageCurrent?int-1}">&laquo;</a></li>
                [/#if]
                [#list 1..view.pageTotal as m]
                    [#if view.pageCurrent==m ]
                        <li class="active"><a href="#">${m} <span class="sr-only">(current)</span></a></li>
                    [#else]
                        <li><a href="/${site.blogPageKey}/${m}">${m}</a></li>
                    [/#if]
                [/#list]
                [#if view.pageCurrent==view.pageTotal]
                    <li class="disabled"><a href="#">&raquo;</a></li>
                [#else]
                    <li><a href="/${site.blogPageKey}/${view.pageCurrent?int+1}">&raquo;</a></li>
                [/#if]
                </ul>
            </div>
        </div>
    [#include "/WEB-INF/template/side.ftl"]
    </div>
</div>
</body>
[#include "/WEB-INF/template/footer.ftl"]