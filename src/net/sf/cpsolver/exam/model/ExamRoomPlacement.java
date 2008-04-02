package net.sf.cpsolver.exam.model;

/**
 * Representation of a room placement of an exam. It contains a room {@link ExamRoom} and a penalty
 * associated with a placement of an exam into the given room.  
 * <br><br>
 * 
 * @version
 * ExamTT 1.1 (Examination Timetabling)<br>
 * Copyright (C) 2008 Tomas Muller<br>
 * <a href="mailto:muller@unitime.org">muller@unitime.org</a><br>
 * Lazenska 391, 76314 Zlin, Czech Republic<br>
 * <br>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <br><br>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <br><br>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
public class ExamRoomPlacement implements Comparable {
    private ExamRoom iRoom;
    private int iPenalty = 0;
    private int iMaxPenalty = 100;

    /**
     * Constructor 
     * @param room examination room
     */
    public ExamRoomPlacement(ExamRoom room) {
        iRoom = room;
    }
    
    /**
     * Constructor 
     * @param room examination room
     * @param penalty penalty for using this room
     */
    public ExamRoomPlacement(ExamRoom room, int penalty) {
        this(room);
        iPenalty = penalty;
    }

    /**
     * Constructor 
     * @param room examination room
     * @param penalty penalty for using this room
     * @param maxPenalty maximal penalty imposed of {@link ExamRoom#getPenalty(ExamPeriod)}, i.e., a placement with greater penalty is not allowed to be made
     */
    public ExamRoomPlacement(ExamRoom room, int penalty, int maxPenalty) {
        this(room, penalty);
        iMaxPenalty = maxPenalty;
    }
    
    /** Examination room */
    public ExamRoom getRoom() {
        return iRoom;
    }
    
    /** Examination room id */
    public long getId() {
        return getRoom().getId();
    }
    
    /** Examination room name */
    public String getName() {
        return getRoom().getName();
    }
    
    /** Examination room availability */
    public boolean isAvailable(ExamPeriod period) {
        return iRoom.isAvailable(period) && iRoom.getPenalty(period)<=iMaxPenalty;
    }
    
    /** Penalty for assignment of an exam into this room {@link Exam#getRoomPlacements()} */
    public int getPenalty() {
        return iPenalty;
    }
    
    /** Maximal penalty imposed of {@link ExamRoom#getPenalty(ExamPeriod)}, i.e., a placement with greater penalty is not allowed to be made */
    public int getMaxPenalty() {
        return iMaxPenalty;
    }

    /** Penalty for assignment of an exam into this room {@link Exam#getRoomPlacements()} */
    public void setPenalty(int penalty) { iPenalty = penalty; }
    
    /** Maximal penalty imposed of {@link ExamRoom#getPenalty(ExamPeriod)}, i.e., a placement with greater penalty is not allowed to be made */
    public void setMaxPenalty(int maxPenalty) { iMaxPenalty = maxPenalty; }

    /** Penalty for assignment of an exam into this room {@link Exam#getRoomPlacements()} and the given examination period
     * @return {@link ExamRoomPlacement#getPenalty()} + {@link ExamRoom#getPenalty(ExamPeriod)}
     */
    public int getPenalty(ExamPeriod period) {
        return iPenalty + iRoom.getPenalty(period);
    }

    /**
     * Room size 
     * @param altSeating examination seeting (pass {@link Exam#hasAltSeating()})
     * @return room size or room alternative size, based on given seating
     */
    public int getSize(boolean altSeating) {
        return (altSeating?getRoom().getAltSize():getRoom().getSize());
    }
    
    /**
     * Room distance
     * @return appropriate {@link ExamRoom#getDistance(ExamRoom)}
     */
    public int getDistance(ExamRoomPlacement other) {
        return getRoom().getDistance(other.getRoom());
    }
    
    /**
     * Hash code
     */
    public int hashCode() {
        return getRoom().hashCode();
    }
    
    /** Compare two room placements for equality */
    public boolean equals(Object o) {
        if (o==null) return false;
        if (o instanceof ExamRoomPlacement) {
            return getRoom().equals(((ExamRoomPlacement)o).getRoom());
        } else if (o instanceof ExamRoom) {
            return getRoom().equals(o);
        }
        return false;
    }
    
    /** Compare two room placements */
    public int compareTo(Object o) {
        if (o==null || !(o instanceof ExamRoomPlacement)) return -1;
        return getRoom().compareTo(((ExamRoomPlacement)o).getRoom());
    }
}