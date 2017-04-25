package rk.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.domain.Sort
import javax.validation.constraints.AssertTrue
import java.time.LocalDateTime

class MeetingRestParams {
    /**
     * value to filter meetings by user id
     */
    @JsonProperty("user")
    Long userId
    /**
     * value to filter meetings by room id
     */
    @JsonProperty("room")
    Long roomId
    /**
     * after it transformation list of meetings contain only that
     * which meetingStart time greater then value
     */
    @JsonProperty("after-time")
    LocalDateTime lowerDateTimeBound
    /**
     * after it transformation list of meetings contain only that
     * which meetingStart time less then value
     */
    @JsonProperty("before-time")
    LocalDateTime upperDateTimeBound
    /**
     * column by which sorting is applied
     */
    @JsonProperty("sort")
    String sortColumn
    /**
     * order of sorting
     */
    @JsonProperty("order")
    Sort.Direction direction
    /**
     * number of page, starts from one
     */
    @JsonProperty("page")
    Integer page

    /**
     * size of page
     */
    @JsonProperty("page-size")
    Integer pageSize

    @AssertTrue
    boolean isValidTimeFilter() {
        lowerDateTimeBound != null && upperDateTimeBound != null && lowerDateTimeBound <= upperDateTimeBound
    }

    @AssertTrue
    boolean isPageSizeValid() {
        pageSize >= 1 && page != null
    }

    @AssertTrue
    boolean isPageValid() {
        page >= 0 && pageSize != null
    }

}
