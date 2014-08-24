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
                    <p class="meta-info"><span
                            class="glyphicon glyphicon-time"></span> ${blogData.created?string("MMMMM d,yyyy")}
                        &nbsp;&nbsp;
                        <span class="glyphicon glyphicon-folder-open"></span>
                        <a href="/${site.blogCategoryKey}/${blogData.category?url}"> ${blogData.category}</a>
                    </p>

                    <div class="post-content">
                        <h2 class="title"><a href="/${site.blogPostKey}/${blogData.title?url}">${blogData.title}</a>
                        </h2>

                        <p>${blogData.content?substring(0,500)}...</p>
                        <a href="/${site.blogPostKey}/${blogData.title?url}" class="more">READ MORE</a>
                    </div>
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
                        [#assign i=((view.pageNo-1)/5)?floor]
                        [#if i<0]
                            [#assign i=0]
                        [/#if]
                        [#if view.pageNo==m ]
                            <li class="active"><a href="#">${m} <span class="sr-only">(current)</span></a></li>
                        [#elseif m<=i*5+5 && m>i*5]
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