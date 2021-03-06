package org.cpsolver.studentsct.heuristics.selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.cpsolver.ifs.heuristics.NeighbourSelection;
import org.cpsolver.ifs.model.Neighbour;
import org.cpsolver.ifs.solution.Solution;
import org.cpsolver.ifs.solver.Solver;
import org.cpsolver.ifs.util.DataProperties;
import org.cpsolver.ifs.util.Progress;
import org.cpsolver.studentsct.heuristics.RandomizedBacktrackNeighbourSelection;
import org.cpsolver.studentsct.model.Enrollment;
import org.cpsolver.studentsct.model.Request;


/**
 * Use backtrack neighbour selection. For all unassigned variables (in a random
 * order), {@link RandomizedBacktrackNeighbourSelection} is being used.
 * 
 * <br>
 * <br>
 * 
 * @version StudentSct 1.3 (Student Sectioning)<br>
 *          Copyright (C) 2007 - 2014 Tomas Muller<br>
 *          <a href="mailto:muller@unitime.org">muller@unitime.org</a><br>
 *          <a href="http://muller.unitime.org">http://muller.unitime.org</a><br>
 * <br>
 *          This library is free software; you can redistribute it and/or modify
 *          it under the terms of the GNU Lesser General Public License as
 *          published by the Free Software Foundation; either version 3 of the
 *          License, or (at your option) any later version. <br>
 * <br>
 *          This library is distributed in the hope that it will be useful, but
 *          WITHOUT ANY WARRANTY; without even the implied warranty of
 *          MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *          Lesser General Public License for more details. <br>
 * <br>
 *          You should have received a copy of the GNU Lesser General Public
 *          License along with this library; if not see
 *          <a href='http://www.gnu.org/licenses/'>http://www.gnu.org/licenses/</a>.
 */

public class BacktrackSelection implements NeighbourSelection<Request, Enrollment> {
    private RandomizedBacktrackNeighbourSelection iRBtNSel = null;
    protected Queue<Request> iRequests = null;

    public BacktrackSelection(DataProperties properties) {
    }

    public void init(Solver<Request, Enrollment> solver, String name) {
        List<Request> unassigned = new ArrayList<Request>(solver.currentSolution().getModel().unassignedVariables(solver.currentSolution().getAssignment()));
        Collections.shuffle(unassigned);
        iRequests = new LinkedList<Request>(unassigned);
        if (iRBtNSel == null) {
            try {
                iRBtNSel = new RandomizedBacktrackNeighbourSelection(solver.getProperties());
                iRBtNSel.init(solver);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        Progress.getInstance(solver.currentSolution().getModel()).setPhase(name, unassigned.size());
    }

    @Override
    public void init(Solver<Request, Enrollment> solver) {
        init(solver, "Backtracking...");
    }
    
    protected synchronized Request nextRequest() {
        return iRequests.poll();
    }

    @Override
    public Neighbour<Request, Enrollment> selectNeighbour(Solution<Request, Enrollment> solution) {
        Request request = null;
        while ((request = nextRequest()) != null) {
            Progress.getInstance(solution.getModel()).incProgress();
            Neighbour<Request, Enrollment> n = iRBtNSel.selectNeighbour(solution, request);
            if (n != null && n.value(solution.getAssignment()) <= 0.0)
                return n;
        }
        return null;
    }

}
