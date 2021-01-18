package com.example.memegenerator.request;

import lombok.Getter;
import lombok.Setter;

public class SocketResponseModel {
    @Getter
    @Setter
    public Long memeId;

    @Getter
    @Setter
    public Boolean isUpvote;

    @Getter
    @Setter
    public Long userId;
}
