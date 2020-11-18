package example.micronaut

import io.micronaut.context.annotation.Value
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import java.util.UUID
import javax.validation.Valid

@Controller
open class BookController {

    @Value("\${stuff.thing}")
    lateinit var thing: String

    @Post
    open fun save(@Valid @Body book: Book): BookSaved {
        val bookSaved = BookSaved()
        bookSaved.name = book.name
        bookSaved.isbn = UUID.randomUUID().toString()
        bookSaved.thing = thing
        return bookSaved
    }
}