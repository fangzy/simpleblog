[#setting locale="en_CN"]
[#include "header.ftl"]
<body>
[#include "nav.ftl"]
<div id="main-area">
    <div class="container">
        <div class="row">
            <div id="posts" class="col-md-8">
            [#assign x=1]
            [#list view.blogDataList as blogData]
                <article id="${"post-"+x}" class="post">
                    <h2 class="title"><a href="/${site.blogPostKey}/${blogData.title?url}">${blogData.title}</a>
                    </h2>

                    <p class="meta-info">
                        <i class="glyphicon glyphicon-time"></i> ${blogData.created?string("yyyy'年'M'月'd'日'")}
                        &nbsp;&nbsp;
                        <i class="glyphicon glyphicon-user"></i>
                        <a href="${site.blogAuthorUrl}"> ${site.blogAuthor}</a>
                        &nbsp;&nbsp;
                        <i class="glyphicon glyphicon-folder-open"></i>&nbsp;
                        <a href="/${site.blogCategoryKey}/${blogData.category?url}"> ${blogData.category}</a>
                    </p>

                    <div class="post-content">
                        <p>${blogData.content}</p>
                    </div>
                    <p class="more pull-right">
                        <a href="/${site.blogPostKey}/${blogData.title?url}">READ MORE</a>
                        <i class="glyphicon glyphicon-chevron-right"></i>
                    </p>
                </article>
                [#assign x=x+1]
            [/#list]
                <div>
                    <ul class="pagination">
                        <li><a href="/${site.blogPageKey}/1">&laquo;</a></li>
                    [#if view.pageNo==1]
                        <li class="disabled"><a href="#">&lsaquo;</a></li>
                    [#else]
                        <li><a href="/${site.blogPageKey}/${view.pageNo?int-1}">&lsaquo;</a></li>
                    [/#if]
                    [#list 1..view.pageTotal as m]
                        [#assign i=((view.pageNo-1)/view.pageSize)?floor]
                        [#if i<0]
                            [#assign i=0]
                        [/#if]
                        [#if view.pageNo==m ]
                            <li class="active"><a href="#">${m} <span class="sr-only">(current)</span></a></li>
                        [#elseif m<=i*view.pageSize+view.pageSize && m>i*view.pageSize]
                            <li><a href="/${site.blogPageKey}/${m}">${m}</a></li>
                        [/#if]
                    [/#list]
                    [#if view.pageNo==view.pageTotal]
                        <li class="disabled"><a href="#">&rsaquo;</a></li>
                    [#else]
                        <li><a href="/${site.blogPageKey}/${view.pageNo?int+1}">&rsaquo;</a></li>
                    [/#if]
                        <li><a href="/${site.blogPageKey}/${view.pageTotal}">&raquo;</a></li>
                    </ul>
                </div>
            </div>
        [#include "side.ftl"]
        </div>
    </div>
</div>
</body>
[#include "footer.ftl"]