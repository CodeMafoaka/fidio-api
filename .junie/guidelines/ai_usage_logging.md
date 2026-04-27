# AI Usage Logging Guideline

Each time a task is completed, Junie must update the `AI_USAGE.md` file at the root of the project.

## File Format

The `AI_USAGE.md` file follows a specific Markdown structure:

```markdown
### AI Usage Log

#### `**Task Title in Bold and Backticks**`

1. **Changes Implemented**:
    *   List major changes with a brief explanation.
    *   Use bold for the change type.

2. **Verification**:
    *   Describe how the changes were verified (e.g., specific commands run, tests passed).

3. **Specification**:
    *   **Model version**: [Real version of the model]
    *   **Agent version**: [Real version of the agent]
    *   **Date**: YYYY-MM-DD
```

## Content Requirements

- **Task Title**: Must be clear, concise, and accurately reflect the primary objective of the task.
- **Changes Implemented**: Provide enough detail for a human reviewer to understand *what* was changed and *why*. Mention specific files if relevant.
- **Verification**: Be specific about the verification process. Include logs or command outputs if they are crucial evidence.
- **Specification**: 
    - Always include the **Model version** and **Agent version**.
    - Use the actual versions provided by the environment or system prompt.
    - Date format must be `YYYY-MM-DD`.

## Update Procedure

1. Append new task logs to the end of the file or replace previous logs if they were part of the same logical task sequence.
2. Ensure consistent indentation and formatting as per the example above.
