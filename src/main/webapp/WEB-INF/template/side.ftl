<div id="side" class="col-md-4">
    <div data-spy="affix" data-offset-top="30" data-offset-bottom="30">
        <div class="panel panel-default">
            <div class="panel-heading">Archives</div>
            <div class="panel-body">
                <ul>
                [#list archiveCount?keys as archiveKey]
                    [#assign timePath=(archiveKey?date("MMMMM yyyy"))?string(site.blogArchiveFormat)]
                    <li>
                        <a href="/${site.blogArchiveKey}/${timePath}">${archiveKey} </a><span
                            class="badge">${archiveCount[archiveKey]}</span>
                    </li>
                [/#list]
                </ul>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">Recent posts</div>
            <div class="panel-body">
                <ul>
                [#list recentTitles as recent]
                    <li><a href="/${site.blogPostKey}/${recent?url}">${recent}</a></li>
                [/#list]
                </ul>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">Random recommends</div>
            <div class="panel-body">
                <ul>
                [#list view.randomTitles as random]
                    <li><a href="/${site.blogPostKey}/${random?url}">${random}</a></li>
                [/#list]
                </ul>
            </div>
        </div>
    </div>
</div>