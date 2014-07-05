<div id="side" class="col-md-4">
    <h5>Categories</h5>
    <ul>
    [#list categoryCount?keys as categoryKey]
        <li>
            <a href="/${"${site.blogCategoryKey}/${categoryKey?url}"}">${categoryKey} </a><span
                class="badge">${categoryCount[categoryKey]}</span>
        </li>
    [/#list]
    </ul>
    <h5>Archives</h5>
    <ul>
    [#list archiveCount?keys as archiveKey]
        [#assign timePath=(archiveKey?date("MMMMM yyyy"))?string(site.blogArchiveFormat)]
        <li>
            <a href="/${site.blogArchiveKey}/${timePath}">${archiveKey} </a><span
                class="badge">${archiveCount[archiveKey]}</span>
        </li>
    [/#list]
    </ul>
    <h5>Recent posts</h5>
    <ul>
    [#list recentTitles as recent]
        <li><a href="/${site.blogPostKey}/${recent?url}">${recent}</a></li>
    [/#list]
    </ul>
[#if view??]
    <h5>Random recommends</h5>
    <ul>
        [#list view.randomTitles as random]
            <li><a href="/${site.blogPostKey}/${random?url}">${random}</a></li>
        [/#list]
    </ul>
[/#if]
</div>