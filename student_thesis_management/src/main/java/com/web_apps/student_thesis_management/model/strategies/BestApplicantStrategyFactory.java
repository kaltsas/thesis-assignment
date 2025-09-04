package com.web_apps.student_thesis_management.model.strategies;

import com.web_apps.student_thesis_management.model.ThesisAssignmentRequest;

public class BestApplicantStrategyFactory {
		private final int RANDOM_CHOISE = 1;
		private final int BEST_GPA_CHOISE = 2;
		private final int FEWEST_REMAINING_COURSES_CHOISE = 3;
		private final int THRESHOLD_CHOISE = 4;

		public BestApplicantStrategy createStrategy(ThesisAssignmentRequest request) {
			int strategyMethod = request.getStrategyMethodFlag();
			if(strategyMethod == RANDOM_CHOISE){
				return new RandomChoiseStrategy();
			}else if(strategyMethod == BEST_GPA_CHOISE){
				return new BestGpaStrategy();
			}else if(strategyMethod == FEWEST_REMAINING_COURSES_CHOISE){
				return new FewestRemainingCoursesStrategy();
			}else if(strategyMethod == THRESHOLD_CHOISE){
				return new ThresholdStrategy(request.getNumberOfRemainingCoursesThreshold(), request.getGradeThreshold());
			}/*this line is never executed*/return null;
		}
}
