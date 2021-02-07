import React from 'react';

interface Props {
  skillText: string;
  abilityText: string;
}

export default function CardFooter({
  skillText,
  abilityText,
}: Props): JSX.Element {
  return (
    <div className={'card-footer'}>
      {skillText !== '' &&
        <>
          <div>Skill:</div>
          <div>{skillText}</div>
        </>
      }
      {abilityText !== '' &&
        <>
          <div>Ability:</div>
          <div>{abilityText}</div>
        </>
      }
    </div>
  );
}