package com.codingblocks.cbonlineapp.database.models

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.*
import com.codingblocks.cbonlineapp.CBOnlineApp
import com.codingblocks.cbonlineapp.util.FileUtils
import com.codingblocks.onlineapi.models.CourseId
import java.sql.Date

@Entity(
    indices = [Index("section_id")],
    foreignKeys = [(ForeignKey(
        entity = SectionModel::class,
        parentColumns = ["csid"],
        childColumns = ["section_id"],
        onDelete = ForeignKey.CASCADE // or CASCADE
    ))]
)
data class CourseContent(
    @PrimaryKey
    var ccid: String,
    var progress: String,
    var progressId: String,
    var title: String,
    var contentDuration: Long,
    var contentable: String,
    var order: Int,
    var section_id: String,
    var attempt_id: String,
    @Embedded
    @Nullable
    var contentLecture: ContentLecture = ContentLecture(),
    @Embedded
    @Nullable
    var contentDocument: ContentDocument = ContentDocument(),
    @Embedded
    @Nullable
    var contentVideo: ContentVideo = ContentVideo(),
    @Embedded
    @Nullable
    var contentQna: ContentQna = ContentQna(),
    @Embedded
    @Nullable
    var contentCode: ContentCodeChallenge = ContentCodeChallenge(),
    @Embedded
    @Nullable
    var contentCsv: ContentCsvModel = ContentCsvModel()
)

//@Entity(
//    primaryKeys = ["section_id", "content_id"],
//    indices = [
//        Index(value = ["section_id"]),
//        Index(value = ["content_id"])
//    ],
//    foreignKeys = [
//        ForeignKey(
//            entity = CourseSection::class,
//            parentColumns = ["csid"],
//            childColumns = ["section_id"]
//        ),
//        ForeignKey(
//            entity = CourseContent::class,
//            parentColumns = ["ccid"],
//            childColumns = ["content_id"]
//        )
//    ]
//)
//data class SectionWithContent(
////        @Nullable @PrimaryKey(autoGenerate = true) val id: Int?,
//    @ColumnInfo(name = "section_id") val sectionId: String,
//    @ColumnInfo(name = "content_id") val contentId: String,
////    @Embedded public User user;
////
////@Relation(parentColumn = "id",
////    entityColumn = "userId") public List<Repo> repoList;
//    val order: Int
//
//)
data class SectionWithContent(
    @Embedded val section: SectionModel,
    @Relation(parentColumn = "csid", entityColumn = "section_id") val content: List<CourseContent>
)

@Entity
data class ContentLecture(
    var lectureUid: String = "",
    var lectureName: String = "",
    var lectureDuration: Long = 0L,
    var lectureId: String = "",
    var lectureContentId: String = "",
    var lectureUpdatedAt: String = "",
    var isDownloaded: String = FileUtils.checkDownloadFileExists(CBOnlineApp.mInstance, lectureId).toString(),
    var date: Date = Date(0L)
)

@Entity
data class ContentDocument(
    var documentUid: String = "",
    var documentName: String = "",
    var documentPdfLink: String = "",
    var documentContentId: String = "",
    var documentUpdatedAt: String = ""
)

@Entity
data class ContentVideo(
    var videoUid: String = "",
    var videoName: String = "",
    var videoDuration: Long = 0L,
    var videoDescription: String? = "",
    var videoUrl: String = "",
    var videoContentId: String = "",
    var videoUpdatedAt: String = ""
)

@Entity
data class ContentCodeChallenge(
    var codeUid: String = "",
    var codeName: String = "",
    var codeProblemId: Int = 0,
    var codeContestId: Int = 0,
    var codeContentId: String = "",
    var codeUpdatedAt: String = ""
)

@Entity
data class ContentQna(
    var qnaUid: String = "",
    var qnaName: String = "",
    var qnaQid: Int = 0,
    var qnaContentId: String = "",
    var qnaUpdatedAt: String = ""
)

@Entity
data class ContentCsvModel(
    var csvUid: String = "",
    var csvName: String = "",
    var csvDescription: String = "",
    var csvContentId: String = "",
    var csvUpdatedAt: String = ""
)

@Entity(
    indices = [Index("contentId")],
    foreignKeys = [(ForeignKey(
        entity = CourseContent::class,
        parentColumns = ["ccid"],
        childColumns = ["contentId"],
        onDelete = ForeignKey.CASCADE // or CASCADE
    ))]
)
data class DoubtsModel(
    @PrimaryKey
    var dbtUid: String = "",
    var title: String = "",
    var body: String = "",
    var contentId: String = "",
    var status: String = "",
    var runAttemptId: String = "",
    var discourseTopicId: String = ""
)

@Entity(
    indices = [Index("contentId")],
    foreignKeys = [(ForeignKey(
        entity = CourseContent::class,
        parentColumns = ["ccid"],
        childColumns = ["contentId"],
        onDelete = ForeignKey.CASCADE // or CASCADE
    ))]
)
data class NotesModel(
    @PrimaryKey
    var nttUid: String = "",
    var duration: Double = 0.0,
    var text: String = "",
    var contentId: String = "",
    var runAttemptId: String = "",
    var createdAt: String = "",
    var deletedAt: String = ""
)

@Entity
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val heading: String,
    val body: String,
    val url: String,
    val seen: Boolean = false,
    val videoId: String = ""
)

@Entity
data class JobsModel(
    @PrimaryKey
    val uid: String,
    val coverImage: String?,
    val ctc: String,
    val deadline: String?,
    val description: String,
    val eligibility: String,
    val experience: String,
    val location: String,
    val postedOn: String,
    val type: String,
    val title: String,
    @Embedded
    val company: Companies,
    val courseId: ArrayList<CourseId>
)

class FormModel(
    val name: String,
    val required: Boolean,
    val title: String,
    val type: String
)

@Entity
data class Companies(
    val id: String,
    val name: String,
    val logo: String,
    val companyDescription: String,
    val website: String
)

