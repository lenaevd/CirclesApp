package app.circles.responses;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class MemberInfo {
    public UUID memberId;
    public String name;
    public String imageUrl;
}
