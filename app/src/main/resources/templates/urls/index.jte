@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.model.Url
@import hexlet.code.util.Format
@param UrlsPage page

@template.layout.page(
page = page,
content = @`
    @if(page.getUrls().isEmpty())
        <p>List is empty</p>
    @else
        <div class="container-lg mt-5">
            <h1>Сайты</h1>
        <table class="table table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <th class="col-1">ID</th>
                    <th>Имя</th>
                    <th class="col-2">Последняя проверка</th>
                    <th class="col-1">Код ответа</th>
                </tr>
                </thead>
            <tbody>
        @for(Url url : page.getUrls())
            <tr>
                <td> ${url.getId()} </td>
                <td> <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a> </td>
                <td> @if (url.getLastCheck()!= null)
                        ${Format.getFormatTime(url.getLastCheck().getCreatedAt())}
                    @endif
                </td>
                <td> @if (url.getLastCheck() != null)
                        ${url.getLastCheck().getStatusCode()}
                    @endif
                </td>
            </tr>
        @endfor
            </tbody>
        </table>
        </div>
    @endif
`
)
