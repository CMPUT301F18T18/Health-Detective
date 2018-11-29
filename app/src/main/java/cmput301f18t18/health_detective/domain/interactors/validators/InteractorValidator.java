package cmput301f18t18.health_detective.domain.interactors.validators;

import cmput301f18t18.health_detective.domain.interactors.base.AbstractInteractor;

public interface InteractorValidator {
    <T extends AbstractInteractor> boolean validateInteractorPermission(T interactor);
}
