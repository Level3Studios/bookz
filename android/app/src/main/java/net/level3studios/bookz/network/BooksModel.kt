package net.level3studios.bookz.network


data class BooksResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<BooksModel>
)

data class BooksModel(
    val kind: String? = null,
    val id: String? = null,
    val etag: String? = null,
    val selfLink: String? = null,
    val volumeInfo: VolumeInfo? = null,
    val saleInfo: SaleInfo? = null,
    val accessInfo: AccessInfo? = null,
    val searchInfo: SearchInfo? = null
)

data class AccessInfo(
    val country: String? = null,
    val viewability: String? = null,
    val embeddable: Boolean? = null,
    val publicDomain: Boolean? = null,
    val textToSpeechPermission: String? = null,
    val epub: Epub? = null,
    val pdf: PDF? = null,
    val webReaderLink: String? = null,
    val accessViewStatus: String? = null,
    val quoteSharingAllowed: Boolean? = null
)

data class Epub(
    val isAvailable: Boolean? = null
)

data class PDF(
    val isAvailable: Boolean? = null,
    val acsTokenLink: String? = null
)

data class SaleInfo(
    val country: String? = null,
    val saleability: String? = null,
    val isEbook: Boolean? = null
)

data class SearchInfo(
    val textSnippet: String? = null
)

data class VolumeInfo(
    val title: String? = null,
    val authors: List<String>? = null,
    val publisher: String? = null,
    val publishedDate: String? = null,
    val description: String? = null,
    val industryIdentifiers: List<IndustryIdentifier>? = null,
    val readingModes: ReadingModes? = null,
    val pageCount: Long? = null,
    val printType: String? = null,
    val categories: List<String>? = null,
    val averageRating: Double? = null,
    val ratingsCount: Long? = null,
    val maturityRating: String? = null,
    val allowAnonLogging: Boolean? = null,
    val contentVersion: String? = null,
    val panelizationSummary: PanelizationSummary? = null,
    val imageLinks: ImageLinks? = null,
    val language: String? = null,
    val previewLink: String? = null,
    val infoLink: String? = null,
    val canonicalVolumeLink: String? = null
) {
    val ratingsCountString: String
        get() {
            return if (this.ratingsCount != null) {
                this.ratingsCount.toString()
            } else {
                "0"
            }
        }

    val averageRatingString: String
        get() {
            return if (this.averageRating != null) {
                this.averageRating.toString()
            } else {
                return "0.0"
            }
        }
}

data class ImageLinks(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null
)

data class IndustryIdentifier(
    val type: String? = null,
    val identifier: String? = null
)

data class PanelizationSummary(
    val containsEpubBubbles: Boolean? = null,
    val containsImageBubbles: Boolean? = null
)

data class ReadingModes(
    val text: Boolean? = null,
    val image: Boolean? = null
)