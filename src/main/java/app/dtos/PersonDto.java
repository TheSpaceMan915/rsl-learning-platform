package app.dtos;

import java.util.List;

public record PersonDto(
        String id,
        String login,
        String clientId,
        String displayName,
        String realName,
        String firstName,
        String lastName,
        String sex,
        String defaultEmail,
        List<String> emails,
        String defaultAvatarId,
        boolean isAvatarEmpty,
        DefaultPhone defaultPhone,
        String psuid
) {

    public static record DefaultPhone(
            long id,
            String number
    ) {}
}
