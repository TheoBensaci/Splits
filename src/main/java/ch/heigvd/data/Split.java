package ch.heigvd.data;

/**
 * @param time time in milli
 */
public record Split(int flagIndex, Flag flag, float time) {

    @Override
    public String toString() {
        return "( "+TimeUtil.timeToString(TimeUtil.getActualTime(time))+" ) "+flag;
    }
}
