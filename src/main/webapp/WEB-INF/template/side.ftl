<div class="col-md-4">
    <div id="side" data-spy="affix" data-offset-top="30" data-offset-bottom="250">
        <div id="archive" class="side-item">
            <div class="side-item-heading">存档</div>
            <ul class="list-group">
            [#list archiveCount?keys as archiveKey]
                [#assign timePath=(archiveKey?date("MMMMM yyyy"))?string(site.blogArchiveFormat)]
                <li class="list-group-item">
                    <a href="/${site.blogArchiveKey}/${timePath}">${archiveKey} </a><span
                        class="badge">${archiveCount[archiveKey]}</span>
                </li>
            [/#list]
            </ul>
        </div>
        <div id="recent" class="side-item">
            <div class="side-item-heading">近期发表</div>
            <ul class="list-group">
            [#list recentTitles as recent]
                <li class="list-group-item">
                    <a href="/${site.blogPostKey}/${recent?url}">${recent}</a>
                </li>
            [/#list]
            </ul>
        </div>
        <div id="recommend" class="side-item">
            <div class="side-item-heading">随机推荐</div>
            <ul class="list-group">
            [#list view.randomTitles as random]
                <li class="list-group-item">
                    <a href="/${site.blogPostKey}/${random?url}">${random}</a>
                </li>
            [/#list]
            </ul>
        </div>
    </div>
</div>