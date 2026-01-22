package entier.person.sale.util;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility xử lý hashtag cho bài viết
 * - Tách hashtag từ nội dung
 * - Chuẩn hóa hashtag
 * - Sinh slug hashtag
 */
public final class HashtagUtil {

    /**
     * Regex:
     * - Bắt #hashtag
     * - Hỗ trợ tiếng Việt
     * - Không bắt ký tự đặc biệt
     */
    private static final Pattern HASHTAG_PATTERN =
            Pattern.compile("#([\\p{L}\\p{N}_-]+)");

    private HashtagUtil() {
        // Utility class - không cho khởi tạo
    }

    /* =====================================================
     * 1️⃣ TÁCH HASHTAG TỪ NỘI DUNG
     * ===================================================== */

    public static List<String> extractFromContent(String content) {
        if (content == null || content.isBlank()) {
            return List.of();
        }

        Set<String> tags = new HashSet<>();
        Matcher matcher = HASHTAG_PATTERN.matcher(content);

        while (matcher.find()) {
            tags.add(matcher.group(1).toLowerCase());
        }

        return new ArrayList<>(tags);
    }

    /* =====================================================
     * 2️⃣ CHUẨN HÓA HASHTAG
     *  - trim
     *  - lowercase
     *  - remove dấu
     *  - unique
     * ===================================================== */

    public static List<String> normalize(Collection<String> hashtags) {
        if (hashtags == null || hashtags.isEmpty()) {
            return List.of();
        }

        return hashtags.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toLowerCase)
                .map(HashtagUtil::removeAccent)
                .distinct()
                .toList();
    }

    /* =====================================================
     * 3️⃣ GỘP HASHTAG (từ request + nội dung)
     * ===================================================== */

    public static List<String> collect(
            Collection<String> requestHashtags,
            String content
    ) {
        Set<String> all = new HashSet<>();

        if (requestHashtags != null) {
            all.addAll(requestHashtags);
        }

        all.addAll(extractFromContent(content));

        return normalize(all);
    }

    /* =====================================================
     * 4️⃣ TẠO SLUG CHO HASHTAG
     * ===================================================== */

    public static String toSlug(String hashtag) {
        if (hashtag == null) return null;

        return removeAccent(hashtag)
                .toLowerCase()
                .replaceAll("[^a-z0-9_-]", "")
                .replaceAll("-{2,}", "-");
    }

    /* =====================================================
     * 5️⃣ REMOVE ACCENT (TIẾNG VIỆT)
     * ===================================================== */

    private static String removeAccent(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
