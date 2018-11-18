package cmput301f18t18.health_detective.presentation.view.activity.listeners;

import cmput301f18t18.health_detective.domain.model.Problem;

public interface ProblemOnClickListener {
    void onDeleteClicked(Problem problem);
    void onEditClicked(Problem problem);
}
