import React from 'react';

const TextAreaInput = ({ name, value, placeholder, onChange, subText, forKey }) => (
    <div className='mb-4 mx-auto w-100 px-3'>

        <label 
            htmlFor={forKey}
            className='form-label'
        >
            {name}
        </label>
        
        <textarea
            key={forKey}
            className="form-control"
            value={value}
            placeholder={placeholder}
            aria-describedby={forKey}
            onChange={onChange}
        />

        {/* Render the sub text if a subtext paramenter is passed */}
        {
            subText ?
                <div className="form-text text-start">
                    {subText}
                </div>
            : null
        }

    </div>
)

export default TextAreaInput;