package io.github.brenoepics

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MentionExtractorTest {

    @Test
    fun `extract mentions with special characters`() {
        val pattern = MentionPattern().withSpecialChars()
        val extractor = MentionExtractor.Builder().pattern(pattern).build()

        val text = "Hey @user1, did you see @user2's message? @user_name.surname"
        val mentions = extractor.fromString(text)

        assertEquals(setOf("user1", "user2", "user_name.surname"), mentions)
    }

    @Test
    fun `extract mentions with email-like pattern`() {
        val pattern = MentionPattern().withEmailLike()
        val extractor = MentionExtractor.Builder().pattern(pattern).build()

        val text = "Send an email to @user@example.com and @another_user@example.net"
        val mentions = extractor.fromString(text)

        assertEquals(setOf("user@example.com", "another_user@example.net"), mentions)
    }

    @Test
    fun `extract mentions with default pattern`() {
        val pattern = MentionPattern().defaultPattern()
        val extractor = MentionExtractor.Builder().pattern(pattern).build()

        val text = "Hey @username, can you help @user_name_123?"
        val mentions = extractor.fromString(text)

        assertEquals(setOf("username", "user_name_123"), mentions)
    }

    @Test
    fun `extract mentions with custom pattern`() {
        val pattern = MentionPattern().customPattern("@([A-Za-z0-9_]+)")
        val extractor = MentionExtractor.Builder().pattern(pattern).build()

        val text = "Let's mention @user_name and @another_user"
        val mentions = extractor.fromString(text)

        assertEquals(setOf("user_name", "another_user"), mentions)
    }

    @Test
    fun `check if text contains mention`() {
        val pattern = MentionPattern().defaultPattern()
        val extractor = MentionExtractor.Builder().pattern(pattern).build()

        val textWithMention = "Hey @username, can you help?"
        val textWithoutMention = "No mention here."

        assertTrue(extractor.containsMention(textWithMention))
        assertFalse(extractor.containsMention(textWithoutMention))
    }

    @Test
    fun `count mentions in text`() {
        val pattern = MentionPattern().withSpecialChars()
        val extractor = MentionExtractor.Builder().pattern(pattern).build()

        val textWithTwoMentions = "Hey @user1, did you see @user2's message?"
        val textWithNoMentions = "No mentions here."

        assertEquals(2, extractor.countMentions(textWithTwoMentions))
        assertEquals(0, extractor.countMentions(textWithNoMentions))
    }

    @Test
    fun `extract limited number of mentions`() {
        val pattern = MentionPattern().defaultPattern()
        val extractor = MentionExtractor.Builder().pattern(pattern).maxMentions(2).build()

        val textWithThreeMentions = "Hey @user1, @user2, and @user3"
        val mentions = extractor.fromString(textWithThreeMentions)

        assertEquals(setOf("user1", "user2"), mentions)
    }

    @Test
    fun `extract mentions from null text`() {
        val pattern = MentionPattern().defaultPattern()
        val extractor = MentionExtractor.Builder().pattern(pattern).build()

        val text = null
        val mentions = extractor.fromString(text)

        assertTrue(mentions.isEmpty())
    }

    @Test
    fun `extract mentions from empty text`() {
        val pattern = MentionPattern().defaultPattern()
        val extractor = MentionExtractor.Builder().pattern(pattern).build()

        val text = ""
        val mentions = extractor.fromString(text)

        assertTrue(mentions.isEmpty())
    }

    @Test
    fun `check if null text contains mention`() {
        val pattern = MentionPattern().defaultPattern()
        val extractor = MentionExtractor.Builder().pattern(pattern).build()

        val text = null

        assertFalse(extractor.containsMention(text))
    }

    @Test
    fun `check if empty text contains mention`() {
        val pattern = MentionPattern().defaultPattern()
        val extractor = MentionExtractor.Builder().pattern(pattern).build()

        val text = ""

        assertFalse(extractor.containsMention(text))
    }

    @Test
    fun `count mentions in null text`() {
        val pattern = MentionPattern().defaultPattern()
        val extractor = MentionExtractor.Builder().pattern(pattern).build()

        val text = null

        assertEquals(0, extractor.countMentions(text))
    }

    @Test
    fun `count mentions in empty text`() {
        val pattern = MentionPattern().defaultPattern()
        val extractor = MentionExtractor.Builder().pattern(pattern).build()

        val text = ""

        assertEquals(0, extractor.countMentions(text))
    }
}
