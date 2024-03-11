package io.github.brenoepics

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse

class MentionPatternTest {

    @Test
    fun `withSpecialChars should include special characters in pattern`() {
        val pattern = MentionPattern().withSpecialChars().build()
        assertTrue(pattern.matcher("@user-name").matches())
        assertTrue(pattern.matcher("@user_name").matches())
        assertTrue(pattern.matcher("@user.name").matches())
        assertTrue(pattern.matcher("@user.").matches())
        assertTrue(pattern.matcher("@user").matches())
        assertTrue(pattern.matcher("@.user").matches())
        assertTrue(pattern.matcher("@user-name.surname").matches())
        assertTrue(pattern.matcher("@user_name.surname").matches())
        assertTrue(!pattern.matcher("hello@user").matches())
        assertTrue(!pattern.matcher("@user!").matches())
        assertTrue(!pattern.matcher("@ user").matches())
    }

    @Test
    fun `withEmailLike should match email-like mentions`() {
        val pattern = MentionPattern().withEmailLike().build()
        assertTrue(pattern.matcher("@user@example.com").matches())
        assertTrue(!pattern.matcher("@user").matches())
        assertTrue(pattern.matcher("@user@example.co.uk").matches())
        assertTrue(pattern.matcher("@user@example.net").matches())
    }

    @Test
    fun `withUnderscores should match mentions with underscores`() {
        val pattern = MentionPattern().withUnderscores().build()
        assertTrue(pattern.matcher("@user_name").matches())
        assertTrue(pattern.matcher("@username").matches())
        assertTrue(!pattern.matcher("@user-name").matches())
        assertTrue(pattern.matcher("@username123").matches())
        assertTrue(pattern.matcher("@user_name_123").matches())
    }

    @Test
    fun `defaultPattern should match any word character after '@'`() {
        val pattern = MentionPattern().defaultPattern().build()
        assertTrue(pattern.matcher("@username").matches())
        assertTrue(pattern.matcher("@user_name").matches())
        assertTrue(!pattern.matcher("@user-name").matches())
        assertTrue(pattern.matcher("@username123").matches())
        assertTrue(pattern.matcher("@user_name_123").matches())
    }

    @Test
    fun `customPattern should match according to the custom pattern`() {
        val pattern = MentionPattern().customPattern("@([A-Za-z0-9_]+)").build()
        assertTrue(pattern.matcher("@user_name").matches())
        assertTrue(pattern.matcher("@username").matches())
        assertTrue(!pattern.matcher("@user-name").matches())
        assertTrue(pattern.matcher("@username123").matches())
        assertTrue(pattern.matcher("@user_name_123").matches())
    }

    @Test
    fun `defaultPattern should not match null or empty strings`() {
        val pattern = MentionPattern().defaultPattern().build()
        assertFalse(pattern.matcher("").matches())
    }

    @Test
    fun `withSpecialChars should not match null or empty strings`() {
        val pattern = MentionPattern().withSpecialChars().build()
        assertFalse(pattern.matcher("").matches())
    }

    @Test
    fun `withEmailLike should not match null or empty strings`() {
        val pattern = MentionPattern().withEmailLike().build()
        assertFalse(pattern.matcher("").matches())
    }

    @Test
    fun `withUnderscores should not match null or empty strings`() {
        val pattern = MentionPattern().withUnderscores().build()
        assertFalse(pattern.matcher("").matches())
    }

    @Test
    fun `customPattern should not match null or empty strings`() {
        val pattern = MentionPattern().customPattern("@([A-Za-z0-9_]+)").build()
        assertFalse(pattern.matcher("").matches())
    }
}
