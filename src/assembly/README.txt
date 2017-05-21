A game of word search

1) Edit run.sh as needed
    To shut down, press ctrl + c or otherwise kill process
    For best result use run.sh instead of starting the jar directly

2) Configuration
    server.port - port game will run on
    server.contextPath - optional context path to use
    service.max.games - to help avoid OOM. NOTE - games are not currently cleared out
    dictionary.file - external dictionary file. Defaults to config/dictionary.txr
    game.board.x - width of board
    game.board.y - height of board
    game.dice - comma separated possible value foreach die/cell. Rows are filled left to right

