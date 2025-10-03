@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.model.Url
@param UrlsPage page

@template.layout.page(
page = page,
content = @`
    <h1>Urls List</h1>
    @if(page.getUrls().isEmpty())
        <p>List is empty</p>
    @else
        @for(Url url : page.getUrls())
            <div>
                <h2>
                    <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a>
                </h2>
            </div>
        @endfor
    @endif
`
)
