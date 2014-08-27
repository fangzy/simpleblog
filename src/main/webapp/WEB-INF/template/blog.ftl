[#setting locale="en_CN"]
[#include "header.ftl"]
<body>
[#include "nav.ftl"]
<div id="main-area">
    <div class="container">
        <div class="row">
            <div id="post" class="col-md-10 col-md-offset-1">
                <article class="post">
                    <h2 class="title"><a
                            href="/${site.blogPostKey}/${view.blogData.title?url}">${view.blogData.title}</a>
                    </h2>

                    <p class="meta-info">
                        <i class="glyphicon glyphicon-time"></i> ${view.blogData.created?string("yyyy'年'M'月'd'日'")}
                        &nbsp;&nbsp;
                        <i class="glyphicon glyphicon-user"></i>
                        <a href="${site.blogAuthorUrl}"> ${site.blogAuthor}</a>
                        &nbsp;&nbsp;
                        <i class="glyphicon glyphicon-folder-open"></i>&nbsp;
                        <a href="/${site.blogCategoryKey}/${view.blogData.category?url}"> ${view.blogData.category}</a>
                    </p>

                    <div class="post-content">
                    ${view.blogData.content}
                    </div>
                </article>
                <ul class="pager">
                [#if view.prevTitle??]
                    <li class="previous"><a href="${view.prevTitle?url}">${"&larr; ${view.prevTitle}"}</a></li>
                [/#if]
                [#if view.nextTitle??]
                <li class="next"><a href="${view.nextTitle?url}">${"${view.nextTitle} &rarr;"}</a>
                [/#if]
                </ul>
            </div>
        [#--[#include "side.ftl"]--]
        </div>
    </div>
</div>
</body>
[#include "footer.ftl"]