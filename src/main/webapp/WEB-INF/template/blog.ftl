[#setting locale="en_CN"]
[#include "header.ftl"]
<body>
<div class="container-fluid">
    <a href="/">回到首页</a>

    <div class="page-header">
        <h1>${view.blogData.title}</h1>

        <h2>
            <small>${(view.blogData.description)!}</small>
        </h2>
        <h4>
            Posted ${view.blogData.created?string("MMMMM d,yyyy")} by <a
                href="${site.blogAuthorUrl}">${site.blogAuthor}</a>.Filed
            under <a href="/${site.blogCategoryKey}/${view.blogData.category?url}">${view.blogData.category}</a>.
        </h4>
    </div>
    <div id="main" class="row">
        <div id="post" class="col-md-8">
            <article>${view.blogData.content}</article>
            <ul class="pager">
            [#if view.prevTitle??]
                <li class="previous"><a href="${view.prevTitle?url}">${"&larr; ${view.prevTitle}"}</a></li>
            [/#if]
            [#if view.nextTitle??]
            <li class="next"><a href="${view.nextTitle?url}">${"${view.nextTitle} &rarr;"}</a>
            [/#if]
            </ul>
        </div>
    [#include "side.ftl"]
    </div>
</div>
</body>
[#include "footer.ftl"]