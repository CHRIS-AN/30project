package re.sta.rt.aop_part2_chapter07

// 상태 값에 따라서 ,다른 UI를 보여주기 위해서 미리 이렇게 지정
enum class State {
    BEFORE_RECORDING,
    ON_RECORDING,
    AFTER_RECORDING,
    ON_PLAYING
}