java_library(
    name = "util",
    srcs = ["Util.java"],
)

java_binary(
    name = "run_util",
    srcs = ["Util.java"],
    main_class = "hw.Util",
)

java_library(
    name = "netlib",
    srcs = [
        "Config.java",
        "NetworkLayer.java",
        "TransportLayer.java",
        "TransportLayerFactory.java",
        "DummyTransportLayer.java",
        "DemoTransportLayer.java",
        "StopAndWait.java",
        "GoBackN.java",
        "Util.java",
    ],
)

java_binary(
    name = "msg_sender",
    srcs = [
        "MsgSender.java",
    ],
    deps = [
        ":netlib",
    ],
    main_class = "hw.MsgSender",
)

java_binary(
    name = "msg_receiver",
    srcs = [
        "MsgReceiver.java",
    ],
    deps = [
        ":netlib",
    ],
    main_class = "hw.MsgReceiver",
)

java_binary(
    name = "file_sender",
    srcs = [
        "FileSender.java",
    ],
    deps = [
        ":netlib",
    ],
    main_class = "hw.FileSender",
)

java_binary(
    name = "file_receiver",
    srcs = [
        "FileReceiver.java",
    ],
    deps = [
        ":netlib",
    ],
    main_class = "hw.FileReceiver",
)
