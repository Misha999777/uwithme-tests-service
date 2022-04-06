const dev = {
    REACT_APP_AUTHORITY: "http://localhost:8080/auth/realms/test",
    REACT_APP_BASE_URL: "http://localhost:8081"
}

const prod = {
    REACT_APP_AUTHORITY: "https://sso.u-with-me.education/auth/realms/tCoMaD",
    REACT_APP_BASE_URL: "https://testsystem.u-with-me.education"
}

export const BASE_URL = process.env.REACT_APP_STAGE === "dev" ? dev.REACT_APP_BASE_URL : prod.REACT_APP_BASE_URL;
export const AUTHORITY = process.env.REACT_APP_STAGE === "dev" ? dev.REACT_APP_AUTHORITY : prod.REACT_APP_AUTHORITY;
export const CLIENT_ID = "TestSystem-UI";