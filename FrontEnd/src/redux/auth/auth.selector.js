import { createSelector } from "reselect";

export const selectAuthReducer = store => store.auth;

export const selectJwt = createSelector(
    [selectAuthReducer],
    auth => auth.jwt
)