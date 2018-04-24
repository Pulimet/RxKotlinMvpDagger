package net.alexandroid.utils.rxkotlinmvpdagger

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.alexandroid.utils.rxkotlinmvpdagger.api.PhotoRetriever
import net.alexandroid.utils.rxkotlinmvpdagger.model.PhotoList
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class JsonUnitTest {

    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    var list: PhotoList? = null
    @Before
    fun setUp() {
        val photoRetriever = PhotoRetriever(OkHttpClient())
        photoRetriever.getPhotosObservable("cat")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ newList: PhotoList? ->
                    list = newList
                })
    }

    @Test
    fun jsonListNotNulTest() {
        assert(list != null)
    }

    @Test
    fun jsonListNotEmptyTest() {
        assert(list?.hits?.isEmpty() == false)
    }

    @Test
    fun jsonListSizeIs20Test() {
        assert(list?.hits?.size?:0 == 20)
    }

    @Test
    fun jsonUrlsAreNoteEmptyTest() {
        list?.hits?.forEach {
            assert(it.previewURL.length > 30)
            assert(it.webformatURL.length > 30)
        }
    }




}
