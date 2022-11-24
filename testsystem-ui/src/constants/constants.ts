export const constants = {
  devMode: (window as any)["env"]["DEV_MODE"],
  serverUrl: (window as any)["env"]["SERVER_URL"],
  keycloakUrl: (window as any)["env"]["KEYCLOAK_URL"],
  client: "uwm-testsystem-ui",
  adminRoles: ["ROLE_ADMIN", "ROLE_TEACHER"]
}
