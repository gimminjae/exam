import React from "react";

function LabelInput(
  labelText: string,
  type: string,
  id: string,
  name: string = "",
  value: string,
  placeholder: string = "",
  onChange: any,
) {
  return (
    <>
      <div>
        {labelText && <label htmlFor={id}>{labelText}</label>}
        {<input
          type={type}
          id={id}
          name={name}
          value={value}
          onChange={onChange}
          placeholder={placeholder}
        />}
      </div>
    </>
  )
}

export default LabelInput