<div class="col-md-4">
    <div id="side" data-spy="affix" data-offset-top="30" data-offset-bottom="250">
        <div class="side-item">
            <div class="side-item-heading">Archives</div>
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
        <div class="side-item">
            <div class="side-item-heading">Recent posts</div>
            <ul class="list-group">
            [#list recentTitles as recent]
                <li class="list-group-item">
                    <a href="/${site.blogPostKey}/${recent?url}">${recent}</a>
                </li>
            [/#list]
            </ul>
        </div>
        <div class="side-item">
            <div class="side-item-heading">Random recommends</div>
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