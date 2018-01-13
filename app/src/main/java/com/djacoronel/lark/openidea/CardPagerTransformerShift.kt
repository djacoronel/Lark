import android.support.v4.view.ViewPager
import android.view.View
import kotlinx.android.synthetic.main.layout_view_idea.view.*

class CardPagerTransformerShift(
        private val baseElevation: Int,
        private val raisingElevation: Int,
        private val smallerScale: Float,
        private val startOffset: Float
): ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val absPosition = Math.abs(position - startOffset)

        if (absPosition >= 1) {
            page.card_idea.cardElevation = baseElevation.toFloat()
            page.scaleY = smallerScale
        } else {
            // This will be during transformation
            page.card_idea.cardElevation = (1 - absPosition) * raisingElevation + baseElevation
            page.scaleY = (smallerScale - 1) * absPosition + 1
        }
    }
}