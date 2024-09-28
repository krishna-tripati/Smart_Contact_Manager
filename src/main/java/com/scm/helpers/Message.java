package com.scm.helpers;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    private String content;
    @Builder.Default
    private MessageType type=MessageType.blue;


}
