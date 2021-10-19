import { createSelector } from "reselect";

export const selectUserReducer = store => store.user;

export const selectIsUserSignedIn = createSelector(
    [selectUserReducer],
    user => user.isUserSignedIn
)