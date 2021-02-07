import React from 'react';

interface Props {
  skillText: string;
  abilityText: string;
  effectText: string;
}

export default function CardFooter({
  skillText,
  abilityText,
  effectText,
}: Props): JSX.Element {
  return (
    <div className={'card-footer'}>
      {!!skillText &&
        <>
          <div>Skill:</div>
          <div>{skillText}</div>
        </>
      }
      {!!abilityText &&
        <>
          <div>Ability:</div>
          <div>{abilityText}</div>
        </>
      }
      {!!effectText &&
        <div>{effectText}</div>
      }
    </div>
  );
}
