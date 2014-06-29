[#setting locale="en_CN"]
[#include "/WEB-INF/template/header.ftl"]
<body>
<div class="container-fluid">
    <a href="/">回到首页</a>
    <div class="page-header">
        <h1>${view.blogData.title}</h1>

        <h2>
            <small>${(view.blogData.description)!}</small>
        </h2>
        <h4>
        ${view.blogData.created?string("MMMMM d, yyyy")}
        </h4>
    </div>
    <div id="main" class="row">
        <div class="col-md-8">
            <div id="post" class="row">
                <div class="col-md-12">
                    <article>${view.blogData.content}</article>
                    <h6>Last modified : ${view.blogData.lastModified?string("MMMMM d, yyyy HH:mm:ss")}</h6>
                    <ul class="pager">
                    [#if view.prevTitle??]
                        <li class="previous"><a href="${view.prevTitle?url}">${"&larr; ${view.prevTitle}"}</a></li>
                    [/#if]
                    [#if view.nextTitle??]
                    <li class="next"><a href="${view.nextTitle?url}">${"${view.nextTitle} &rarr;"}</a>
                    [/#if]
                    </ul>
                </div>
            </div>
        </div>
    [#include "/WEB-INF/template/side.ftl"]
    </div>
</div>
</body>
[#include "/WEB-INF/template/footer.ftl"]