load("@rules_proto//proto:defs.bzl", "proto_library")

proto_library(
    name = "data_proto",
    srcs = ["data.proto"],
)


java_proto_library(
    name = "data_java_proto",
    deps = ["data_proto"],
)

java_binary(
    name = "snake_server",
    srcs = [
        "SnakeServer.java",
        "Config.java",
        "GameState.java",
        "Position.java",
        "NetUtil.java",
    ],
    deps = [":data_java_proto"],
    main_class = "snake.app.SnakeServer",
)

java_binary(
    name = "snake_client",
    srcs = [
        "SnakeClient.java",
        "Config.java",
        "Board.java",
        "DirectionChangeHandler.java",
        "NetUtil.java",
    ],
    data = glob(["images/*"]),
    deps = [":data_java_proto"],
    main_class = "snake.app.SnakeClient",
)
